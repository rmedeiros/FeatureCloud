var randomProperty = function (object) {
    var keys = Object.keys(object);
    return object[keys[Math.floor(keys.length * Math.random())]];
};


var colorGraduation = function (total, current) {
    var percent = current * 100 / total;
    return (Math.floor(percent * 255 / 100)).toString(16);
}


var addUniqueGroup = function (group, groupSet) {
    if (groupSet.find(e = > e.idDeveloperGroup == group.idDeveloperGroup) ==
    undefined
)
    groupSet.push(group)
}

var compareDevGroups = function compare(a, b) {
    if (a.idDeveloperGroup > b.idDeveloperGroup) return 1;
    if (b.idDeveloperGroup > a.idDeveloperGroup) return -1;

    return 0;
}