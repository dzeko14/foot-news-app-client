package my.dzeko.newsparser.entities

data class ParsedNews(
    var title :String,
    var summary :String,
    var date :Long,
    var parsedTags :List<ParsedTag>,
    var content :List<String>,
    var images: List<String>,
    var originalUrl :String
)