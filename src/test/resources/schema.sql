
-- -----------------------------------------------------
-- Schema FEATURE_CLOUD
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema FEATURE_CLOUD


-- -----------------------------------------------------
-- Table component_package
-- -----------------------------------------------------
CREATE TABLE  component_package (
  idpackage INT NOT NULL,
  name VARCHAR(45) NULL,
  isroot INT NULL,
  isnew INT NULL,
  PRIMARY KEY (idpackage));


-- -----------------------------------------------------
-- Table core_asset
-- -----------------------------------------------------
CREATE TABLE  core_asset (
  idcoreasset INT NOT NULL,
  name VARCHAR(200) NULL,
  path VARCHAR(200) NULL,
  size INT NULL,
  isNewAsset INT NULL,
  content LONGTEXT NULL,
  idpackage INT NOT NULL,
  PRIMARY KEY (idcoreasset),
  CONSTRAINT fk_core_asset_component_package1
    FOREIGN KEY (idpackage)
    REFERENCES component_package (idpackage)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);



-- -----------------------------------------------------
-- Table feature_group
-- -----------------------------------------------------
CREATE TABLE  feature_group (
  id_feature_group INT NOT NULL,
  feature_expression VARCHAR(200) NOT NULL,
  PRIMARY KEY (id_feature_group));



-- -----------------------------------------------------
-- Table variation_point
-- -----------------------------------------------------
CREATE TABLE  variation_point (
  idvariationpoint INT NOT NULL,
  expression LONGTEXT NULL,
  idcoreasset INT NOT NULL,
  id_feature_group INT NULL,
  body LONGTEXT NULL,
  lines_number INT NOT NULL,
  PRIMARY KEY (idvariationpoint),
  CONSTRAINT fk_ProductAssetVP_ProductAsset1
    FOREIGN KEY (idcoreasset)
    REFERENCES core_asset (idcoreasset)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_VariationPoint_Feature_group1
    FOREIGN KEY (id_feature_group)
    REFERENCES feature_group (id_feature_group)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);



-- -----------------------------------------------------
-- Table developer_group
-- -----------------------------------------------------
CREATE TABLE  developer_group (
  id_developer_group INT NOT NULL,
  PRIMARY KEY (id_developer_group));



-- -----------------------------------------------------
-- Table product_release
-- -----------------------------------------------------
CREATE TABLE  product_release (
  idproductrelease VARCHAR(200) NOT NULL,
  name VARCHAR(200) NULL,
  date DATETIME NULL,
  commits_set LONGTEXT NULL,
  PRIMARY KEY (idproductrelease));



-- -----------------------------------------------------
-- Table customization_fact
-- -----------------------------------------------------
CREATE TABLE  customization_fact (
  idcustomization INT NOT NULL,
  lines_added INT NULL,
  lines_deleted INT NULL,
  custom_diff LONGTEXT NULL,
  commit_set LONGTEXT NULL,
  message_set LONGTEXT NULL,
  greater_diff LONGTEXT NULL,
  type VARCHAR(200) NULL,
  idvariationpoint INT NOT NULL,
  id_developer_group INT NOT NULL,
  idproductrelease VARCHAR(200) NOT NULL,
  PRIMARY KEY (idcustomization),
  CONSTRAINT fk_Customization_ProductAssetVP
    FOREIGN KEY (idvariationpoint)
    REFERENCES variation_point (idvariationpoint)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_CustomizationFact_developer_group1
    FOREIGN KEY (id_developer_group)
    REFERENCES developer_group (id_developer_group)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_CustomizationFact_ProductRelease1
    FOREIGN KEY (idproductrelease)
    REFERENCES product_release (idproductrelease)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);



-- -----------------------------------------------------
-- Table developer
-- -----------------------------------------------------
CREATE TABLE  developer (
  iddeveloper INT NOT NULL,
  name VARCHAR(200) NULL,
  email VARCHAR(100) NULL,
  PRIMARY KEY (iddeveloper));



-- -----------------------------------------------------
-- Table parent_feature
-- -----------------------------------------------------
CREATE TABLE  parent_feature (
  idparentfeature INT NOT NULL,
  name VARCHAR(45) NULL,
  responsible VARCHAR(45) NULL,
  PRIMARY KEY (idparentfeature));



-- -----------------------------------------------------
-- Table feature
-- -----------------------------------------------------
CREATE TABLE  feature (
  idfeature VARCHAR(200) NOT NULL,
  name VARCHAR(200) NULL,
  isNew INT NULL,
  idparent INT NOT NULL,
  PRIMARY KEY (idfeature),
  CONSTRAINT fk_feature_ParentFeature1
    FOREIGN KEY (idparent)
    REFERENCES parent_feature (idparentfeature)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);



-- -----------------------------------------------------
-- Table feature_bridge
-- -----------------------------------------------------
CREATE TABLE  feature_bridge (
  id_feature_group INT NOT NULL,
  id_feature VARCHAR(200) NOT NULL,
  PRIMARY KEY (id_feature_group, id_feature),
  CONSTRAINT fk_Feature_group_has_Feature_Feature_group1
    FOREIGN KEY (id_feature_group)
    REFERENCES feature_group (id_feature_group)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Feature_group_has_Feature_Feature1
    FOREIGN KEY (id_feature)
    REFERENCES feature (idfeature)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);



-- -----------------------------------------------------
-- Table developer_bridge
-- -----------------------------------------------------
CREATE TABLE  developer_bridge (
  id_developer_group INT NOT NULL,
  id_developer INT NOT NULL,
  PRIMARY KEY (id_developer_group, id_developer),
  CONSTRAINT fk_developer_bridge_developer_group1
    FOREIGN KEY (id_developer_group)
    REFERENCES developer_group (id_developer_group)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_developer_bridge_Developer1
    FOREIGN KEY (id_developer)
    REFERENCES developer (iddeveloper)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE products (
  product_name varchar(250) DEFAULT NULL,
  product_id varchar(25) NOT NULL,
  PRIMARY KEY (product_id))
;


CREATE TABLE product_features (
  product_id varchar(250) NOT NULL,
  feature_id varchar(250) NOT NULL,
  PRIMARY KEY (product_id,feature_id),
  CONSTRAINT feature_id FOREIGN KEY (feature_id) REFERENCES feature (idfeature),
  CONSTRAINT product_id FOREIGN KEY (product_id) REFERENCES products (product_id)
);

-- -----------------------------------------------------
-- View new_assets_in_products
-- -----------------------------------------------------

CREATE OR REPLACE VIEW new_assets_in_products AS

select     ca.idcoreasset as idasset, ca.name as name, ca.path as path, ca.content as content, ca.size as size, pr.idproductrelease as pr_id, pr.name as pr_name
from core_asset ca inner join variation_point vp  on ca.idcoreasset = vp.idcoreasset
inner join customization_fact c on c.idvariationpoint = vp.idvariationpoint
inner join product_release pr on pr.idproductrelease = c.idproductrelease
where isNewAsset=1;

-- -----------------------------------------------------
-- View features_in_variationpoints
-- -----------------------------------------------------

CREATE OR REPLACE VIEW features_in_variationpoints AS
    SELECT 
           
        vp.idvariationpoint AS id_variationpoint,
        vp.expression AS expression,
        f.name AS feature_name,
        f.idfeature AS id_feature,
        ca.idcoreasset AS id_coreasset,
        fb.id_feature_group AS id_feature_group

    FROM
        ((((variation_point vp
        JOIN core_asset ca ON ((vp.idcoreasset = ca.idcoreasset)))
        JOIN feature_group fg ON ((fg.id_feature_group = vp.id_feature_group)))
        JOIN feature_bridge fb ON ((fb.id_feature_group = fg.id_feature_group)))
        JOIN feature f ON ((f.idfeature = fb.id_feature)));

-- -----------------------------------------------------
-- View churn_productportfolio_features
-- -----------------------------------------------------

CREATE OR REPLACE VIEW churn_productportfolio_features AS

select    f.idfeature as id_feature ,f.name as featuremodified, pr.idproductrelease as id_pr, pr.name as pr_name,
 sum(c.lines_added) as added, sum(c.lines_deleted) as deleted, sum(c.lines_added+c.lines_deleted) as churn
from customization_fact c inner join variation_point vp
on c.idvariationpoint = vp.idvariationpoint
inner join feature_group fg on fg.id_feature_group = vp.id_feature_group 
inner join feature_bridge fb on fb.id_feature_group = fg.id_feature_group
inner join feature f on f.idfeature = fb.id_feature
inner join product_release pr on pr.idproductrelease = c.idproductrelease
group by f.idfeature, pr.idproductrelease;

-- -----------------------------------------------------
-- View churn_coreassets_and_features_by_pr
-- -----------------------------------------------------

CREATE OR REPLACE VIEW churn_coreassets_and_features_by_pr AS

select     ca.idcoreasset as idcoreasset, pr.idproductrelease as idproductrelease , pr.name as pr_name, ca.name as ca_name,
ca.path as ca_path, f.idfeature as idfeature, sum(c.lines_added + c.lines_deleted) as churn

from customization_fact c inner join variation_point vp
on c.idvariationpoint = vp.idvariationpoint
inner join core_asset ca on ca.idcoreasset = vp.idcoreasset
inner join feature_group fg on fg.id_feature_group = vp.id_feature_group 
inner join feature_bridge fb on fb.id_feature_group = fg.id_feature_group
inner join feature f on f.idfeature = fb.id_feature
inner join product_release pr on pr.idproductrelease = c.idproductrelease
group by f.idfeature, pr.idproductrelease, ca.idcoreasset;

-- -----------------------------------------------------
-- View coreassets_and_features
-- -----------------------------------------------------

CREATE OR REPLACE VIEW coreassets_and_features AS

select     ca.idcoreasset as idcoreasset, ca.name as caname, ca.path as capath, ca.idpackage as idpackage,
f.idfeature as idfeature, ca.size as size
from variation_point vp inner join core_asset ca on vp.idcoreasset = ca.idcoreasset
inner join feature_group fg on fg.id_feature_group = vp.id_feature_group 
inner join feature_bridge fb on fb.id_feature_group = fg.id_feature_group
inner join feature f on f.idfeature = fb.id_feature;

-- -----------------------------------------------------
-- View customs_by_feature_and_coreasset
-- -----------------------------------------------------

CREATE OR REPLACE VIEW customs_by_feature_and_coreasset AS

select     c.idcustomization as idcustomization, f.idfeature as idfeature, ca.idcoreasset as idcoreasset, f.idparent as idparentfeature, pf.name as parentfeaturename,
ca.name as caname, ca.path as capath, pr.idproductrelease as idproductrelease , pr.name as prname, c.lines_added as added,
c.lines_deleted as deleted,
c.custom_diff as custom_diff, c.message_set as messages, c.commit_set as commits, c.greater_diff as maindiff

from customization_fact c inner join variation_point vp
on c.idvariationpoint = vp.idvariationpoint
inner join feature_group fg on fg.id_feature_group = vp.id_feature_group 
inner join feature_bridge fb on fb.id_feature_group = fg.id_feature_group
inner join feature f on f.idfeature = fb.id_feature
inner join parent_feature pf on pf.idparentfeature = f.idparent
inner join product_release pr on pr.idproductrelease = c.idproductrelease
inner join core_asset ca on ca.idcoreasset = vp.idcoreasset;

-- -----------------------------------------------------
-- View churn_parent_features_productportfolio
-- -----------------------------------------------------

CREATE OR REPLACE VIEW churn_parent_features_productportfolio AS

select    pf.idparentfeature as id_parentfeature, pf.name as parentfeaturename, pr.idproductrelease as id_pr, pr.name as pr_name,
 sum(c.lines_added) as added, sum(c.lines_deleted) as deleted, sum(c.lines_added+c.lines_deleted) as churn
from customization_fact c inner join variation_point vp
on c.idvariationpoint = vp.idvariationpoint
inner join feature_group fg on fg.id_feature_group = vp.id_feature_group 
inner join feature_bridge fb on fb.id_feature_group = fg.id_feature_group
inner join feature f on f.idfeature = fb.id_feature
inner join parent_feature pf on pf.idparentfeature = f.idparent
inner join product_release pr on pr.idproductrelease = c.idproductrelease
group by pf.idparentfeature, pr.idproductrelease;

-- -----------------------------------------------------
-- View churn_parent_features_product_packages
-- -----------------------------------------------------

CREATE OR REPLACE VIEW churn_parent_features_product_packages AS

select    pf.idparentfeature as id_parentfeature, pf.name as parentfeaturename, pr.idproductrelease as idproductrelease, pr.name as pr_name, cp.idpackage as idpackage, cp.name as package_name, cp.isroot as isroot,
 sum(c.lines_added) as added, sum(c.lines_deleted) as deleted, sum(c.lines_added+c.lines_deleted) as churn


from customization_fact c inner join variation_point vp
on c.idvariationpoint = vp.idvariationpoint
inner join core_asset ca on ca.idcoreasset = vp.idcoreasset
inner join component_package cp on cp.idpackage = ca.idpackage
inner join feature_group fg on fg.id_feature_group = vp.id_feature_group 
inner join feature_bridge fb on fb.id_feature_group = fg.id_feature_group
inner join feature f on f.idfeature = fb.id_feature
inner join parent_feature pf on pf.idparentfeature = f.idparent
inner join product_release pr on pr.idproductrelease = c.idproductrelease
group by pf.idparentfeature, pr.idproductrelease, cp.idpackage;

-- -----------------------------------------------------
-- View churn_parent_features_package_assets
-- -----------------------------------------------------

CREATE OR REPLACE VIEW churn_parent_features_package_assets AS

select    pf.idparentfeature as id_parentfeature, pf.name as parentfeaturename, pr.idproductrelease as idproductrelease, pr.name as pr_name, cp.idpackage as idpackage, cp.name as package_name, 
cp.isroot as isroot, ca.idcoreasset as idcoreasset, ca.name as caname, ca.path as capath,
 sum(c.lines_added) as added, sum(c.lines_deleted) as deleted, sum(c.lines_added+c.lines_deleted) as churn


from customization_fact c inner join variation_point vp
on c.idvariationpoint = vp.idvariationpoint
inner join core_asset ca on ca.idcoreasset = vp.idcoreasset
inner join component_package cp on cp.idpackage = ca.idpackage
inner join feature_group fg on fg.id_feature_group = vp.id_feature_group 
inner join feature_bridge fb on fb.id_feature_group = fg.id_feature_group
inner join feature f on f.idfeature = fb.id_feature
inner join parent_feature pf on pf.idparentfeature = f.idparent
inner join product_release pr on pr.idproductrelease = c.idproductrelease
group by pf.idparentfeature, pr.idproductrelease, cp.idpackage, ca.idcoreasset;

-- -----------------------------------------------------
-- View churn_features_product_packages
-- -----------------------------------------------------

CREATE OR REPLACE VIEW churn_features_product_packages AS

select    f.idfeature as idfeature, f.name as featurename, f.idparent as idparentfeature, pr.idproductrelease as idproductrelease, pr.name as pr_name, cp.idpackage as idpackage, cp.name as package_name, cp.isroot as isroot,
 sum(c.lines_added) as added, sum(c.lines_deleted) as deleted, sum(c.lines_added+c.lines_deleted) as churn


from customization_fact c inner join variation_point vp
on c.idvariationpoint = vp.idvariationpoint
inner join core_asset ca on ca.idcoreasset = vp.idcoreasset
inner join component_package cp on cp.idpackage = ca.idpackage
inner join feature_group fg on fg.id_feature_group = vp.id_feature_group 
inner join feature_bridge fb on fb.id_feature_group = fg.id_feature_group
inner join feature f on f.idfeature = fb.id_feature
inner join product_release pr on pr.idproductrelease = c.idproductrelease
group by f.idfeature, pr.idproductrelease, cp.idpackage;

-- -----------------------------------------------------
-- View churn_features_package_assets
-- -----------------------------------------------------

CREATE OR REPLACE VIEW churn_features_package_assets AS

select    f.idfeature as idfeature, f.name as featurename, f.idparent as idparentfeature, pr.idproductrelease as idproductrelease, pr.name as pr_name, 
cp.idpackage as idpackage, cp.name as package_name, cp.isroot as isroot, ca.idcoreasset as idcoreasset, ca.name as caname, ca.path as capath,
 sum(c.lines_added) as added, sum(c.lines_deleted) as deleted, sum(c.lines_added+c.lines_deleted) as churn


from customization_fact c inner join variation_point vp
on c.idvariationpoint = vp.idvariationpoint
inner join core_asset ca on ca.idcoreasset = vp.idcoreasset
inner join component_package cp on cp.idpackage = ca.idpackage
inner join feature_group fg on fg.id_feature_group = vp.id_feature_group 
inner join feature_bridge fb on fb.id_feature_group = fg.id_feature_group
inner join feature f on f.idfeature = fb.id_feature
inner join product_release pr on pr.idproductrelease = c.idproductrelease
group by f.idfeature, pr.idproductrelease, cp.idpackage, ca.idcoreasset;




