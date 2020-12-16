import dendropy
from flask import request
from flask_restful import Resource
from db_queries import db_queries
from TreecloudFunctions import *
import json


def matched(str):
    count = 0
    opened=0
    closed=0
    for i in str:
        if i == "(":
            count += 1
            opened += 1
        elif i == ")":
            count -= 1
            closed += 1
        if count < 0:
            return False
    print(closed)
    print(opened)
    return count == 0


class NewickGenerator(Resource):

    def post(self):
        queries=  db_queries()
        groups = queries.get_modified_groups()
        features = request.json
        coocurrence={}
        #    coocc[i][j][0] is the number of windows containing feature i and feature j
        #    coocc[i][j][1] is the number of windows not containing feature i but containing feature j
        #    coocc[i][j][2] is the number of windows containing feature i but not feature j
        #    coocc[i][j][3] is the number of windows containing neither word i nor word j
        for i in features:
            coocurrence[features.index(i)]={}
            for j in features:
                coocurrence[features.index(i)][features.index(j)]={}
                coocurrence[features.index(i)][features.index(j)][0]=0
                coocurrence[features.index(i)][features.index(j)][1]=0
                coocurrence[features.index(i)][features.index(j)][2]=0
                coocurrence[features.index(i)][features.index(j)][3]=0
                for group in groups.items():
                        if i in group[1] and j in group[1]:
                            coocurrence[features.index(i)][features.index(j)][0]=coocurrence[features.index(i)][features.index(j)][0]+1
                        elif i in group[1]:
                            coocurrence[features.index(i)][features.index(j)][1]=coocurrence[features.index(i)][features.index(j)][1]+1
                        elif  j in group[1]:
                            coocurrence[features.index(i)][features.index(j)][2]=coocurrence[features.index(i)][features.index(j)][2]+1
                        else:
                            coocurrence[features.index(i)][features.index(j)][3]=coocurrence[features.index(i)][features.index(j)][3]+1
        distance = normalizeMatrix(distanceFromCooccurrence(coocurrence,'jaccard'),'auto')
        fileName = exportToCsv(distance,features)
        pdm = dendropy.PhylogeneticDistanceMatrix.from_csv(
            src=open(fileName),
            delimiter=";")
        nj_tree = pdm.nj_tree()
        tree= nj_tree.as_string("newick")
        if not matched(tree):
            tree= tree.replace(';',');')
        print (tree)
        return tree


