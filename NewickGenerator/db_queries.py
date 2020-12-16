from db import mysql
class db_queries():

    def __init__(self):
        return

    def get_modified_features(self):
        cursor = mysql.get_db().cursor()
        cursor.execute(' SELECT distinct (ID_FEATURE) FROM FEATURES_IN_VARIATIONPOINTS INNER JOIN CUSTOMIZATION_FACT ON ID_VARIATIONPOINT=IDVARIATIONPOINT WHERE ID_FEATURE!="NO FEATURE";')
        data = cursor.fetchall()
        return [feature[0] for feature in data]

    def get_modified_groups(self):
        cursor = mysql.get_db().cursor()
        cursor.execute(' SELECT fg.ID_FEATURE_GROUP, ID_FEATURE FROM feature_group fg inner join feature_bridge fb on fg.ID_FEATURE_GROUP=fb.ID_FEATURE_GROUP inner join variation_point vp on vp.ID_FEATURE_GROUP=fg.ID_FEATURE_GROUP inner join customization_fact cf on vp.IDVARIATIONPOINT=cf.IDVARIATIONPOINT where id_feature!="No Feature";')
        data = cursor.fetchall()
        groups = {}
        for group in data:
            groups[group[0]]=[]
        for group in data:
            groups[group[0]].append(group[1])
        return groups