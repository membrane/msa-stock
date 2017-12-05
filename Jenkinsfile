node {
        stage("Checkout") {
            checkout scm
        }

        stage('Maven Build') {
            echo $SHELL
            sh "mvn package"
        }

        stage('Docker image') {
             app = docker.build("membrane/msa-stock")
        }
}