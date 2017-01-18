require 'onesky'

# Create client
client = Onesky::Client.new('4SE1TdvPn48nctD9UmRnOMnacTjVHF2C', ENV['ONE_SKY_SECRET_KEY'])

# show project details
project_id = 94974
project = client.project(project_id)
resp = JSON.parse(project.show)
p resp['data']

# upload file
resp = project.upload_file(file: 'app/src/main/res/values/strings.xml', file_format: 'ANDROID_XML', is_keeping_all_strings: 'false')
p resp.code # => 202
