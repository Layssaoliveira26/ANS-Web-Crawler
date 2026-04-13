package tasks

import org.jsoup.Jsoup
import utils.FileUtils

class TableErrorsTask {

    static final String URL_TABELAS = "https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/padrao-tiss-tabelas-relacionadas"

    static void executar() {

        try {
            def doc = Jsoup.connect(URL_TABELAS)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(15000)
                    .get()

            def links = doc.select("a[href]")

            def linkTabela = links.find { link ->
                link.text().toLowerCase().contains("tabela de erros no envio para a ans")
            }

            if (linkTabela) {
                def urlArquivo = linkTabela.absUrl("href")
                def nomeArquivo = urlArquivo.tokenize("/").last()

                FileUtils.baixarArquivo(
                        urlArquivo,
                        "downloads/Tabelas/${nomeArquivo}"
                )
            } else {
                println "Link da tabela de erros não encontrado."
            }

        } catch (Exception e) {
            println "Erro na Task 3: ${e.message}"
            e.printStackTrace()
        }
    }
}