# FeatureCloud
[![Build Status](https://travis-ci.com/rmedeiros/FeatureCloud.svg?branch=feature%2Fsnapshot-visualization)](https://travis-ci.com/rmedeiros/FeatureCloud)
    [![License](https://img.shields.io/badge/license-GPL-blue)](https://www.gnu.org/licenses/gpl-3.0.html)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=rmedeiros_FeatureCloud&metric=alert_status)](https://sonarcloud.io/dashboard?id=rmedeiros_FeatureCloud)

FeatureCloud is a tag based visualization tool used to improve comprehensibility and maintenance
of preprocessor-based SPL code. A quick demo is available at [https://vimeo.com/338522586](https://vimeo.com/338522586)


## Requirements
Java 8 +

Maven 3.6 +

Python 2.7


## Installation & Usage

### With Docker
The easiest way of running FeatureCloud is with Docker: 

1. Install docker on your computer
2. Open a terminal in the root of the project
3. run docker-compose up

### Mannualy
If you want to build and run FeatureCloud on your own, you have to follow these steps:
1. Install FeatureCloud [requirements](#requirements)
2. Install python needed packages ([python reqs](src/main/resources/requirements.txt)):

     ``` $ pip install -r src/main/resources/requirements.txt  ```
3. Package the jar using Maven:

     ``` $ mvn package  ```
4. Run the jar with Java:

     ``` $ java -jar target/featurecloud-[VERSION].jar  ```

    

## Based on

* [d3.js](http://d3js.org/)
* [Bootstrap](http://getbootstrap.com)
* [jQuery](http://jquery.com)
* [Spring Boot](https://github.com/spring-projects/spring-boot)
* [PhyloPlot](https://github.com/adamzy/PhyloPlot)
