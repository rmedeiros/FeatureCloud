function featureMouseOverEventHandler() {
    let featureName = event.currentTarget.innerHTML
    $.ajax({
        type: "GET",
        url: path + "/tangled-features/" + featureName,
        success: function (data) {
            let tangledFeaturesElements = Array.from(document.querySelectorAll("text")).filter(function (element) {
                return data.includes(element.innerHTML)
            })
            tangledFeaturesElements.forEach(function (x) {
                x.setAttribute('font-weight', 'bold');
                let color = x.getAttribute('fill').replace("0000ff", "00b300");
                x.setAttribute('fill', color);
            });
        },
        contentType: "application/json"
    });

}

function featureMouseOutEventHandler() {
    let featureName = event.currentTarget.innerHTML
    $.ajax({
        type: "GET",
        url: path + "/tangled-features/" + featureName,
        success: function (data) {
            let tangledFeaturesElements = Array.from(document.querySelectorAll("text")).filter(function (element) {
                return data.includes(element.innerHTML)
            })
            tangledFeaturesElements.forEach(function (x) {
                x.setAttribute('font-weight', '')
                let color = x.getAttribute('fill').replace("00b300", "0000ff");
                x.setAttribute('fill', color);
            });
        },
        contentType: "application/json"
    });

}


