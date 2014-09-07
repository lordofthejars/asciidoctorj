var convert = function(content, hash2) {
    return Opal.Asciidoctor.$convert(content, hash2);
};

var convert_file = function(file, hash2) {
    return Opal.Asciidoctor.$convert_file(file, hash2);
};