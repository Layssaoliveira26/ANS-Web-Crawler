package tasks

import org.jsoup.Jsoup
import utils.FileUtils

class DownloaderTask {
    static final String URL_ANS = "https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/padrao-tiss-marco-2026"

    static void executar() {
        try {
            def doc = Jsoup.connect(URL_ANS)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/144.0.0.0 Safari/537.36")
                .timeout(150000)
                .get()

            def linhas = doc.select("tr")
            def linkComunicacao = null

            linhas.each{ linha ->
                if (linha.text().toLowerCase().contains("componente de comunicação")) {
                    linkComunicacao = linha.select("a.btn-primary").first()
                }
            }

            if (linkComunicacao) {
                def urlArquivo = linkComunicacao.absUrl("href")
                def nomeArquivo = urlArquivo.tokenize("/").last()

                FileUtils.baixarArquivo(urlArquivo, "downloads/Arquivos_padrao_TISS/${nomeArquivo}")
            } else {
                println "Link do componente de comunicação não encontrado"
            }

        } catch(Exception e) {
            println "Erro na task 1: ${e.message}"
            e.printStackTrace()
        }
    }
}