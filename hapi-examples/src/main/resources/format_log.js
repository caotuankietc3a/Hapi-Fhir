var fs = require("fs");

function format_json(object) {
    for (const key in object) {
        if (!object){
            return null;
        }
        if (Object.hasOwnProperty.call(object, key)) {
            let element = object[key];
            if (typeof(element)==  "object"){
                element = format_json(element);
                for (const k in element) {
                    if (Object.hasOwnProperty.call(element, k)) {
                        const newKey = key +"__" + k;
                        object[newKey]= element[k];
                        delete element[k];
                    }
                }
                delete object[key]
            }

        }
    }
    return object;
}
function chunkArray(array, size) {
    var result = [];
    while (array.length > 0) {
    var chunk = array.splice(0, size);
    var newData = chunk.join("\n");
    result.push(newData); 
    }
    return result;
    }
fs.readFile("application.log", "utf8", function (err, data) {
    if (err) {
        console.error(err);
    } else {
        var lines = data.split("\n");
        newLines = [];
        const a= new Date();
        for (let index = 0; index < lines.length; index++) {
            let line = lines[index];
            let new_line = null;
            try {
                line = JSON.parse(JSON.stringify(line))
                new_line = JSON.parse(line);
                new_line = format_json(new_line)
                newLines.push(JSON.stringify(new_line));
            } catch (error) {

            } 

        }
        var fileData = chunkArray(newLines, 500);
        for (let index = 0; index < fileData.length; index++) {
            const element = fileData[index];
            fs.writeFile("out_6_1_"+index+".log", element, function (err) {
                if (err) {
                    console.error(err);
                } else {
                    console.log("File updated successfully");
                }
            });
        }
       
    }
});