library(data.table)
request_data <- read.csv("data/request.csv",header = TRUE, sep = ",")
# 设置为本地时间
Sys.setenv(TZ='Asia/Shanghai')
# 将时间转化为timesstamp(秒，暂未找到转成毫秒的函数)
request_data[,'timestamp'] <- as.numeric(strptime(request_data[,'time'], format="%Y-%m-%dT%H:%M:%S"))
# 将path用 '?' 分割成两部分，取第一部分
request_data[,'api'] <- tstrsplit(as.character(request_data[,'path']), "?", fixed = TRUE)[1]
# 分割出的第一部分字符串的最后一个字符若不是 '/' 则添加一个'/', 方便后面正则匹配替换
request_data[,'api'] <- gsub(pattern = "//", replacement = "/", paste(request_data[,'api'],"/",sep = ""))
# 不知道java的map在r中怎么使用，暂时用了一个data.frame存储正则规则
patterns <- c("projects/.*?/", "branches/.*?/", "potential_students/.*?/", "students/.*?/", "guardians/.*?/", "coaches/.*?", "fields/.*?", "course_schedules/.*?", "metrics/.*?") 
replacements <- c("projects/{projectId}/", "branches/{branchId}/", "potential_students/{potential_student_Id}/", "students/{studentId}/", "guardians/{guardianId}/", "coaches/{coachId}/", "fields/{fieldId}/", "course_schedules/{course_schedule_id}/", "metrics/{metricName}/")
data_map <- data.frame(patterns, replacements)
# 正则替换
for (i in 1:dim(data_map)[1]) {
  request_data[,'api'] <- gsub(pattern = data_map[,'patterns'][i],replacement = data_map[,'replacements'][i], x = request_data[,'api'])
}
# 去掉最后一个字符 '/'
request_data[,'api'] <- substr(request_data[,'api'],start = 1, stop = nchar(request_data[,'api']) -1)
# 保存数据
write.csv(file='./data/request.csv', x=request_data, row.names = F)

# 计算response中的timestamp(秒)
response_data <- read.csv("data/response.csv",header = TRUE, sep = ",")
response_data[,'timestamp'] <- as.numeric(strptime(response_data[,'time'], format="%Y-%m-%dT%H:%M:%S"))
write.csv(file='./data/response.csv', x=response_data, row.names = F)