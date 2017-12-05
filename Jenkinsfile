node {
        stage("Checkout") {
            checkout scm
        }

        stage('Maven Build') {
            sh "echo $SHELL"
            sh "mvn package"
        }

        stage('Docker image') {
             app = docker.build("membrane/msa-stock")
        }
}