"""
User controller
"""
from flask import request
from json import dumps, loads
from requests import codes

from retrofit2server.resources.user import (
    User as UserResource,
    UserList as UserListResource,
)


def create_user_routes(app):
    user_list = []

    @app.route("/api/user", methods=["POST"])
    def create_user():
        app.logger.info("content-type: {}, data: {}".format(
            request.headers.get('content-type'),
            request.get_data(),
        ))

        user_resource = UserResource.from_dict(loads(request.get_data()))
        user_resource.id = len(user_list)
        user_list.append(user_resource)
        return dumps(user_resource.to_dict()), codes.created

    @app.route("/api/user/<int:user_id>", methods=["GET"])
    def get_user(user_id):
        app.logger.info("content-type: {}, user_id: {}".format(
            request.headers.get('content-type'),
            user_id,
        ))
        if (user_id < 0 or user_id >= len(user_list)):
            return "", codes.not_found
        return dumps(user_list[user_id].to_dict()), codes.ok

    @app.route("/api/users", methods=["GET"])
    def get_users():
        app.logger.info("content-type: {}".format(request.headers.get('content-type')))  # noqa

        if len(user_list) <= 0:
            return dumps("{}"), codes.ok

        user_list_resource = UserListResource(items=user_list)
        return dumps(user_list_resource.to_dict()), codes.ok
