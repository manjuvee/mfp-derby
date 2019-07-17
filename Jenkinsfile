node ('mfp-icp-svl') {
   def BUILD_TIMESTAMP
   
   stage('Tag and Prep for Build') {
      deleteDir()
      sshagent([env.MFPJENKINS_SSHAGENT_KEY]) {
         sh 'git clone -b ' + env.BRANCH_NAME + ' git@github.ibm.com:MobileFirst/ibm-mobilefoundation-containers.git' + " ."
         TimeZone tz = TimeZone.getTimeZone("Asia/Kolkata");
         BUILD_TIMESTAMP = new Date().format("YYYYMMdd-HHmmssSSS", tz) + '-' + env.BUILD_NUMBER
         sh 'git remote -v && git tag ' + BUILD_TIMESTAMP + ' && git push origin --tags'
      }
   }
   
   println "BUILD_TIMESTAMP : " + BUILD_TIMESTAMP
   println "BRANCH NAME : " + env.BRANCH_NAME
   
   stage('Build ibm-mobilefoundation-containers') {
      println "ARTIFACTORY_SERVER : " + env.ARTIFACTORY_SERVER
      println "ARTIFACTORY_PATH : " + env.ARTIFACTORY_PATH
      println "DOCKER_REGISTRY : " + env.DOCKER_REGISTRY
      
      withEnv(['ART_SERVER=' + ARTIFACTORY_SERVER, 'ART_PATH=' + ARTIFACTORY_PATH, 'DOC_REG=' + DOCKER_REGISTRY]) {
         withCredentials([string(credentialsId: 'ICP_ARTIFACTORY_API_KEY', variable: 'ART_API_KEY')]) {
            withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: '581fb1e5-833c-4894-a80f-9f2e81184c26', usernameVariable: 'USER_NAME', passwordVariable: 'PASSWORD']]) {
               sh '''
                  set +x
                  export DOCKER_CLI_EXPERIMENTAL=enabled
                  export ARTIFACTORY_API_KEY=${ART_API_KEY}
                  export REPO_URL=${DOCKER_REGISTRY}/build
                  export WLP_VERSION=`sed -n 's:.*<wlp.version>\\(.*\\)</wlp.version>.*:\\1:p' ibm-mobilefoundation-base/pom.xml`
                  
                  docker login ${REPO_URL} -u ${USER_NAME} -p ${PASSWORD}
                  mvn clean install -s maven-settings.xml
                  set -x
               '''
            }
         }
      }

      stage('Build ibm-mobilefoundation-prod') {
         withEnv(['ART_SERVER=' + ARTIFACTORY_SERVER, 'ART_PATH=' + ARTIFACTORY_PATH, 'DOC_REG=' + DOCKER_REGISTRY, 'BRANCH=' + env.BRANCH_NAME, 'TIMESTAMP=' + BUILD_TIMESTAMP]) {
            withCredentials([string(credentialsId: 'ICP_ARTIFACTORY_API_KEY', variable: 'ART_API_KEY')]) {
               withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: '581fb1e5-833c-4894-a80f-9f2e81184c26', usernameVariable: 'USER_NAME', passwordVariable: 'PASSWORD']]) {
                  sh '''
                     STABLE_BUILD_DIR="repo/stable"
                     set +x
                     git clone -b ${BRANCH} git@github.ibm.com:MobileFirst/ibm-mobilefoundation-prod.git
                     #git clone git@github.ibm.com:MobileFirst/ibm-mobilefoundation-prod.git
                     cd ibm-mobilefoundation-prod
                     #wget https://github.ibm.com/IBMPrivateCloud/content-verification/releases/download/v1.3.8/cv-linux-amd64.tar.gz
                     #gunzip cv-linux-amd64.tar.gz
                     #mv cv /usr/local/bin/
                     export REPO_URL=${DOCKER_REGISTRY}/build
                     export REGISTRY_USERNAME=${USER_NAME}
                     export REGISTRY_PASSWORD=${PASSWORD}
                     export ARTIFACTORY_API_KEY=${ART_API_KEY}
                     export VERSION=`grep version stable/ibm-mobilefoundation-prod/Chart.yaml | awk '{print $2}'`
                     
                     make clean ppa
                     #if [[ "${BRANCH}" = "master" || "${BRANCH}" = "development" ]]; then 
                        curl -H X-JFrog-Art-Api:${ARTIFACTORY_API_KEY} -T ./${STABLE_BUILD_DIR}/IBM-MobileFoundation-Enterprise-Pak.tar.gz "${ART_SERVER}/${ART_PATH}/${BRANCH}/${TIMESTAMP}/IBM-MobileFoundation-Enterprise-Pak-${VERSION}.tar.gz"
                     #fi;
                     set -x
                  '''
               }
            }
         }
      }
   }
}
            

