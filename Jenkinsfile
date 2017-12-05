node {
        stage("checkout") {
            checkout scm
        }

        stage('Docker image') {
                app = docker.build("membrane/msa-stock")
        }
}