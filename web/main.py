import os
from flask import Flask, send_from_directory
from database import UPLOAD_DIR
from public import public
from admin import admin
from api import api

app = Flask(__name__)
app.secret_key = os.environ.get("SECRET_KEY", "dev-only-change-me")
app.register_blueprint(public)
app.register_blueprint(api, url_prefix='/api')
app.register_blueprint(admin, url_prefix='/admin')


@app.route('/uploads/<path:name>')
def uploads(name):
    """Files uploaded while running in demo mode (stored in /tmp)."""
    return send_from_directory(UPLOAD_DIR, name)


if __name__ == '__main__':
    app.run(debug=True, port=5008, host="0.0.0.0")
