#### Stage 1: Build the application
FROM maven:3.6.2-jdk-8  as build

# Set the current working directory inside the image
WORKDIR /app


# Copy the pom.xml file
COPY pom.xml .

# Build all the dependencies in preparation to go offline.
# This is a separate step so the dependencies will be cached unless
# the pom.xml file has changed.
RUN mvn dependency:go-offline -B

# Copy the project source
COPY src src



# Package the application
RUN mvn package -DskipTests
RUN ls target
RUN pwd

#Install python2.7 required for calculating the Newick tree
RUN apt-get update \
    && apt-get upgrade -y \
    && apt-get install -y \
    python-pip \
    python2.7 \
    python2.7-dev \
    && apt-get autoremove \
    && apt-get clean

#Install python requirements
RUN pip install dendropy
RUN mv target/featurecloud-*.jar target/featurecloud.jar

ENTRYPOINT ["java","-jar","target/featurecloud.jar"]