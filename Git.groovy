/**
 * Functions pertaining to git.
 */

def version = '1.0'


def tagAndPush(tagName, jenkinsCredentialsId) {
  stage "Tagging Git Commit"
  sshagent([jenkinsCredentialsId]) {
    sh 'set +x && git tag ' + tagName + ' && git push origin ' + tagName
  }
}

def getGitOrgName() {
    def gitRemote = getGitRemote()
    def matcher = gitRemote =~ 'origin	https://github\\.ibm\\.com'
    def regexPattern = matcher ? 'https://github\\.ibm\\.com/(.*)/' : ':(.*)/'
    matcher = gitRemote =~ regexPattern
    def orgName = matcher ? matcher[0][1] : "unknown"
    matcher = null
    return orgName
}

def getGitRepositoryName() {
    def gitRemote = getGitRemote()
    def matcher = gitRemote =~ 'origin	https://github\\.ibm\\.com'
    def regexPattern = matcher ? 'https://github\\.ibm\\.com/.*/(.*)\\.git' : '/(.*)\\.git'
    matcher = gitRemote =~ regexPattern
    def repositoryName = matcher ? matcher[0][1] : "unknown"
    matcher = null
    return repositoryName
}

def getGitRemote() {
    def gitRemote = sh script: 'git remote -v', returnStdout: true
    return gitRemote.trim()
}

def getGitSlug() {
  def org = getGitOrgName()
  def repo = getGitRepositoryName()
  def branch = env.BRANCH_NAME
  return "${org}:${repo}:${branch}"
}

def getBuildSlug() {
  def gitSlug = getGitSlug()
  def buildNumber = env.BUILD_NUMBER
  return "${gitSlug}:${buildNumber}"
}

def getGitCommit() {
  def gitCommit = sh script: "git log --pretty=format:'%H %s' -1", returnStdout: true
  return gitCommit.trim()
}

def getBuildNumber() {
  def number = env.BUILD_NUMBER
  try {
    echo BuildNumber
    def matcher = BuildNumber =~ '.*-(\\d+)\\.zip.*'
    number = matcher ? matcher[0][1] : 'Unknown'
    matcher = null
  } catch (err) {
    // Intentionally empty
  }
  echo 'Deploying build number = ' + number
  return number
}

return this
