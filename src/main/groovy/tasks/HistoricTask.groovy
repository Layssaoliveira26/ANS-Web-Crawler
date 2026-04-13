package tasks

import org.jsoup.Jsoup

class HistoricTask {

    static final String URL_HISTORICO = "https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/padrao-tiss-historico-das-versoes-dos-componentes-do-padrao-tiss"

    static void executar() {

        try {
            def doc = Jsoup.connect(URL_HISTORICO)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .timeout(15000)
                .get()

            def tabela = doc.select("table").first()

            if (!tabela) {
                println "Tabela não encontrada."
                return
            }

            def linhas = tabela.select("tr")
            def dados = []

            linhas.eachWithIndex { linha, i ->
                if (i == 0) return

                def colunas = linha.select("td")
                if (colunas.size() >= 3) {
                    def competencia = colunas[0].text().trim()
                    def publicacao  = colunas[1].text().trim()
                    def inicioVig   = colunas[2].text().trim()

                    if (filtrarAPartirDe2016(competencia)) {
                        dados << [competencia, publicacao, inicioVig]
                    }
                }
            }

            salvarCSV(dados)

        } catch (Exception e) {
            println "Erro na Task 2: ${e.message}"
            e.printStackTrace()
        }
    }

    private static boolean filtrarAPartirDe2016(String competencia) {
        try {
            // Lida com "jan/2016", "Jan/2016", "JAN/2016"
            def partes = competencia.toLowerCase().split("/")
            if (partes.size() < 2) return false
            int ano = Integer.parseInt(partes[1].trim())
            return ano >= 2016
        } catch (Exception e) {
            return false
        }
    }

    private static void salvarCSV(List dados) {
        def raiz = System.getProperty("user.dir")
        def arquivo = new File("${raiz}/downloads/Historico/historico_tiss.csv")

        arquivo.parentFile.mkdirs()

        arquivo.withWriter("UTF-8") { writer ->
            writer.writeLine('"Competência","Publicação","Início de Vigência"')
            dados.each { linha ->
                writer.writeLine("\"${linha[0]}\",\"${linha[1]}\",\"${linha[2]}\"")
            }
        }

        println "Histórico salvo com sucesso!"
    }
}