from flask import Flask

app = Flask(__name__.split('.')[0])
app.debug = True

from retrofit2server.controllers.user import create_user_routes  # noqa

# Setup the routes
create_user_routes(app)
