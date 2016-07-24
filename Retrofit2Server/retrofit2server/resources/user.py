from retrofit2server.resources.base import Resource, PaginatedResourceList


class User(Resource):

    MEDIA_TYPE = 'application/vnd.retrofit2example.user+json'

    def __init__(self, id, name,):
        self.id = id
        self.name = name

    def __repr__(self):
        return "<User id: {}, name: {}".format(self.id, self.name)

    def to_dict(self):
        return dict(id=self.id, name=self.name)

    @classmethod
    def from_dict(cls, dct):
        return cls(id=dct.get("id"), name=dct.get("name"))


class UserList(PaginatedResourceList):
    """
    Contact list for a owner
    """
    MEDIA_TYPE = 'application/vnd.retrofit2example.user.list+json'

    @classmethod
    def items_name(cls):
        return "users"

    @classmethod
    def items_class(cls):
        return User
