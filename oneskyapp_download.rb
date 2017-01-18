require 'onesky'
require 'fileutils'

# Create client
client = Onesky::Client.new('4SE1TdvPn48nctD9UmRnOMnacTjVHF2C', ENV['ONE_SKY_SECRET_KEY'])

# show project details
project_id = 94974
project = client.project(project_id)
resp = JSON.parse(project.show)
p resp['data']

# get language list
resp = JSON.parse(project.list_language)
#p resp['data']

# download translations that are ready
resp['data'].each do |x|
  if (x['is_ready_to_publish'])
    locale = x['custom_locale'] || x['locale']
    print "Downloading ", x['english_name'], " (", x['code'], ") with translation progress of ", x['translation_progress'], " to values-", locale, "... "
    resp = project.export_translation(source_file_name: 'strings.xml', locale: x['code'])

    path = 'app/src/main/res/values-' + locale
    FileUtils.mkdir_p(path) unless File.exists?(path)
    File.open(path + '/strings.xml', 'w') { |file| file.write(resp)}

    # exclude languages with supersets: pt-PT, en-XX
    if (!(locale.eql?"pt-rPT" || locale.start_with?("en-")))
      File.open(path + '/localization.xml', 'w') { |file|
        file << "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
        file << "<resources>\n"
        file << "    <bool name=\"localized\">true</bool>\n"
        file << "    <fraction name=\"localization_progress\">" + x['translation_progress'] + "</fraction>\n"
        file << "</resources>\n"
      }
    end
    puts "Done!"
  end
end