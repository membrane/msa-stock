node {
        stage("Checkout") {
            checkout scm
        }

        stage('Maven Build') {
            sh "echo $SHELL"
            sh "mvn package"
        }

        stage('Docker image') {
             docker.build("membrane/msa-stock")
        }

        stage("Deploy") {
            sh "docker rm -f stock"
            sh "docker run -d --name stock membrane/msa-stoc"
        }
}