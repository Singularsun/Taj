library(data.table)
prepare_data <- function() {
  reg_data <- read.csv("data/reg_data.csv",header = TRUE, sep = ",")
  src_data <- read.csv("data/src_data.csv",header = TRUE, sep = ",")
  # 设置为本地时间
  Sys.setenv(TZ='Asia/Shanghai')
  # 将时间转化为timesstamp(秒，暂未找到转成毫秒的函数)
  src_data[,'timestamp'] <- as.numeric(strptime(src_data[,'req.time'], format="%Y-%m-%dT%H:%M:%S"))
  # 添加partition_day, partition_hour
  src_data$partition.day <- paste0(substr(src_data$req.time,start = 1, stop = 10))
  src_data$partition.hour <- paste0(substr(src_data$req.time,start = 1, stop = 13), ":00")
  # 将path用 '?' 分割成两部分，取第一部分
  src_data[,'api'] <- tstrsplit(as.character(src_data[,'path']), "?", fixed = TRUE)[1]
  # 添加一个'/', 方便后面正则匹配替换
  src_data[,'api'] <- paste(src_data[,'api'],"/",sep = "")
  # 正则替换
  for (i in 1:dim(reg_data)[1]) {
    src_data[,'api'] <- gsub(pattern = reg_data[,'patterns'][i],replacement = reg_data[,'replacements'][i], x = src_data[,'api'])
  }
  # 去掉之前添加的最后一个字符 '/'
  src_data[,'api'] <- substr(src_data[,'api'],start = 1, stop = nchar(src_data[,'api']) -1)
  
  write.csv(file='./output/prepared_data.csv', x=src_data, row.names = F)
}