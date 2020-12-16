from flask import Flask
from flask_restful import Api
from flaskext.mysql import MySQL
from db import mysql
from NewickTreeGenerator import NewickGenerator

app = Flask(__name__)
app.config['MYSQL_DATABASE_USER'] = 'spl'
app.config['MYSQL_DATABASE_PASSWORD'] = 'spl'
app.config['MYSQL_DATABASE_DB'] = 'featurecloud'
app.config['MYSQL_DATABASE_HOST'] = '158.227.113.216'
app.config['MYSQL_DATABASE_PORT'] = 3306
api = Api(app)

mysql.init_app(app)

api.add_resource(NewickGenerator, '/newick-tree')
if __name__ == '__main__':
    app.run()  # important to mention debug=True