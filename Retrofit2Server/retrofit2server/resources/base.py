from abc import ABCMeta, abstractmethod, abstractproperty


class Resource(object):
    __metaclass__ = ABCMeta

    @classmethod
    def media_type(cls):
        return cls.MEDIA_TYPE

    def _dict_no_none(self, **kwargs):
        return {
            k: v
            for k, v in kwargs.iteritems()
            if v is not None
        }

    @abstractmethod
    def to_dict(self):
        pass

    @classmethod
    def from_dict(cls, resource_dict):
        raise NotImplementedError()

    def __hash__(self):
        return hash(frozenset(self.to_dict()))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.to_dict() == other.to_dict()  # noqa

    def __ne__(self, other):
        return self.to_dict() != other.to_dict()


class ResourceList(Resource):

    @abstractproperty
    def resources(self):
        pass

    def __len__(self):
        return len(self.resources)

    def __iter__(self):
        return iter(self.resources)

    def append(self, resource):
        self.resources.append(resource)


class PaginatedResourceList(ResourceList):

    def __init__(self, items=None, offset=None, limit=None, total_count=None):
        self.items = items or []
        self.offset = offset
        self.limit = limit
        self.total_count = total_count

    @property
    def resources(self):
        return self.items

    @classmethod
    def items_name(cls):
        raise NotImplementedError("`items_name` is not implemented")

    @classmethod
    def items_class(cls):
        raise NotImplementedError("`items_class` is not implemented")

    def to_dict(self):
        return {
            self.items_name(): [
                entry.to_dict() for entry in self
            ],
            "offset": self.offset,
            "limit": self.limit,
            "totalCount": self.total_count,
        }

    @classmethod
    def from_dict(cls, dct):
        return cls(
            [cls.items_class().from_dict(model) for model in dct[cls.items_name()]],  # noqa
            offset=dct["offset"],
            limit=dct["limit"],
            total_count=dct["totalCount"],
        )
