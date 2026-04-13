import tasks.DownloaderTask
import tasks.HistoricTask
import tasks.TableErrorsTask

println "Web Crawler - ANS"

DownloaderTask.executar();
HistoricTask.executar();
TableErrorsTask.executar();

println "Finalizada task";