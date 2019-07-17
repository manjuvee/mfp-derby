# ibm-mobilefoundation-containers

| master  | development	|
|:---------------:|:----:|
| ![Build Status](https://travis.ibm.com/MobileFirst/ibm-mobilefoundation-containers.svg?token=J31d31Q7Rm8hxbSVeG9x&branch=master) | ![Build Status](https://travis.ibm.com/MobileFirst/ibm-mobilefoundation-containers.svg?token=J31d31Q7Rm8hxbSVeG9x&branch=development)

For issues, use the [mfp-cloud-planning](https://github.ibm.com/MobileFirst/mfp-cloud-planning/) repository

This repository contains the sources for building the docker containers for IBM Mobile Foundation components. The components are: 
<ul>
<li>Core runtime / server (mfpf-server)</li>
<li>Analytics (mfpf-analytics)</li>
<li>Application Center (mfpf-appcenter)</li>
</ul>

The repository is enabled for CI using Travis CI. Pull request, development and master branches are enabled for automatic build and deploy. The details of the builds can be found [here](https://travis.ibm.com/MobileFirst/ibm-mobilefoundation-containers). 

The final deliverables from this repository are docker images for the components listed above that are tagged and pushed to a container repository (docker / artifactory)

## Git Flow

- <b>development</b> branch: Contains pre-production code. All features that are complete development are merged here.
- <b>master</b> branch: Contains production / released code. All code from development branch is merged to master at sometime

## Build & run locally

### Prerequisites

1. Apache [Maven](https://maven.apache.org/download.cgi)
2. Java 1.7+
3. Git CLI
3. Docker

### Clone the repository

```
git clone git@github.ibm.com:MobileFirst/ibm-mobilefoundation-containers.git
cd ibm-mobilefoundation-containers
```

### Set the environment

```
export DOCKER_CLI_EXPERIMENTAL=enabled
export ARTIFACTORY_API_KEY=<ARTIFACTORY-API-KEY-FOR-WLP-ZIP>
export REPO_URL=<REPO-URL>
export REGISTRY_USERNAME=<USERID-TO-REGISTRY>
export REGISTRY_PASSWORD=<PASSWORD-TO-REGISTRY>
```

### Docker login

```
docker login ${REPO_URL} -u ${REGISTRY_USERNAME} -p ${REGISTRY_PASSWORD}
```

- If you're using MacOS, ensure that the docker config has the right credentials set to be able to access the repository. The credentials are stored in file `~/.docker/config.json` 

```
	"auths": {
		"<REPO_URL>": {  "auth": "<BASE64_ENCODED_CREDS>" }
	}
```

- BASE64_ENCODED_CREDS to be provided in the above "auth" element can be obtained using: 

```
echo -n ${REGISTRY_USERNAME}:${REGISTRY_PASSWORD} | base64
```


### Build

- To build the images and push to the registry, use:

```
mvn clean install -s maven-settings.xml
```

- To build the images locally:

```
mvn clean install -Pdev -s maven-settings.xml
```

### Run

<< TO BE DONE >>


## Release

Release is done by merging 'development' branch to 'master'. After the merge to master, the version of the images is incremented. The versions are maintained in the 'image.tag' property in the parent pom.
       
