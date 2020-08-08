#!groovy
@Library("btech-pipeline-library")
import software.btech.pipeline.ArchiveUtility
import software.btech.pipeline.docker.DockerClientUtility

// constants
final BRANCH_NAME = "1.x"
final ARCHIVE_NAME = "ai-pilot-" + BRANCH_NAME + ".tar.gz"
final ORIGIN_GIT_CREDENTIALS_ID = "github_credentials"
final ORIGIN_GIT_URL = "git@github.com:bsantanna/ai-pilot.git"

// pipeline utilities
final archiveUtility = new ArchiveUtility(this)
final dockerUtility = new DockerClientUtility(this, [:])

def cleanupResources(DockerClientUtility dockerUtility) {
  // clean resources
  dockerUtility.runContainerWithCommand("bsantanna/alpine", pwd(), "/opt/workspace", null, [
      "rm -fr /opt/workspace/*"
  ])
}

catchError {

  stage("Pipeline setup") {
    node("gitClient") {
      // cleanup workspace
      deleteDir()

      // checkout
      git credentialsId: ORIGIN_GIT_CREDENTIALS_ID, url: ORIGIN_GIT_URL, branch: BRANCH_NAME

      // stash
      stash name: "sources"

      // cleanup resources
      cleanupResources(dockerUtility)
    }
  }


  stage("Build") {

    node("dockerClient&&x86_64") {
      // cleanup workspace
      deleteDir()

      // unstash
      unstash "sources"

      // unarchive
      archiveUtility.unarchiveFile(ARCHIVE_NAME)

      // build backend
      dockerUtility.runContainer("bsantanna/maven-build", pwd(), "/opt/workspace")

      // stash build
      stash name: "build"

      // cleanup resources
      cleanupResources(dockerUtility)
    }

  }

}
