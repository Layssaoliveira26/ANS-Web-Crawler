# ANS Web Crawler

Bot desenvolvido em Groovy para coletar dados públicos do site da Agência Nacional de Saúde Suplementar (ANS), focado na documentação do padrão TISS.

## O que ele faz

- **Task 1** — Baixa o Componente de Comunicação do padrão TISS (versão mais recente)
- **Task 2** — Coleta o histórico de versões dos componentes a partir de jan/2016 e salva em CSV
- **Task 3** — Baixa a tabela de erros no envio para a ANS (.xlsx)

## Tecnologias

- Groovy
- Jsoup (parse e scraping do HTML)
- HTTPBuilder (requisições HTTP)
- Gradle

## Como rodar

Clone o repositório e execute:

```bash
./gradlew run
```

Os arquivos baixados ficam salvos na pasta `downloads/` na raiz do projeto.

## Observação

Os dados coletados são todos de acesso público, disponíveis no portal do governo em:
https://www.gov.br/ans
