package utils

class FileUtils {

    public static void baixarArquivo(String url, String caminhoDestino) {
        def raiz = System.getProperty("user.dir")
        def arquivo = new File("${raiz}/${caminhoDestino}")

        arquivo.parentFile.mkdirs()

        new URL(url).withInputStream { inputStream ->
            arquivo.withOutputStream { outputStream ->
                outputStream << inputStream
            }
        }
        println "salvo em ${caminhoDestino}"
    }
}