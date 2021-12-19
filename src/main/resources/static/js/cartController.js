function getCookie(cname) {
    var name = cname + '=';
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return '';
}

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = 'expires='+ d.toUTCString();
    document.cookie = cname + '=' + cvalue + ';' + expires + ';path=/';
}




let cartArray = []
let strCookie = getCookie("pizza")
if(strCookie !== ''){
    cartArray = JSON.parse(strCookie);
}



function getDataById(id){
    const url = "/api/v1/item/" + id + "/cartData";
    $.get(url, null, (data) =>{
        let find = false
        for(const i in cartArray){
            if(cartArray[i].key === data['id']){
                find = true
                cartArray[i].count += 1
                break
            }
        }
        if(find === false){
            cartArray.push({
                key: data['id'],
                value: data,
                count: 1
            })
        }
        renderCart()
    })

}

function myFunction() {
    if(cartArray.length !== 0){
        document.getElementById("myDropdown").classList.toggle("show");
    }
}

function renderCart(){

    $("#cart").empty();
    for (let i in cartArray) {
        let item_data = cartArray[i]
        let item = item_data.value
        const template = `<tr>
                                        <td class="text-center align-middle"><img width="32px" src="${item['urlToImage']}"></td>
                                        <td class=" align-middle">${item['name']}</td>
                                        <td class="text-center align-middle">
                                            <input type="number" onchange="changeCount(${item['id']},this)" class="form-control text-center" value="${item_data.count}">
                                        </td>
                                        <td class="text-center align-middle">${item['price'] * item_data.count}</td>
                                        <td class="text-center align-middle"> <button onclick="setZero(${item['id']})" class="btn btn-close"></button> </td>
                                    </tr>`
        $("#cart").append(template)
    }
    updatePrice()
    setCookie("pizza", JSON.stringify(cartArray), 1)
    if(cartArray.length === 0){
        document.getElementById("myDropdown").classList.remove("show");
    }

}

function setZero(id){
    for (let i in cartArray) {
        if(cartArray[i].key === id){
            cartArray = cartArray.filter(item => item !== cartArray[i])
            break
        }
    }
    renderCart()
}

function changeCount(id,obj){
    for (let i in cartArray) {
        if(cartArray[i].key === id){
            cartArray[i].count = obj.value;
            if(cartArray[i].count == 0){
                cartArray = cartArray.filter(item => item !== cartArray[i])
            }
            break
        }
    }
    renderCart()
}

function sendCart(){
    $.post("/api/v1/order/make", {'data':JSON.stringify(cartArray)}, (data)=>{
        console.log(data);
    })
}


function updatePrice(){
    let sum = 0;
    for (let i in cartArray) {
        sum += cartArray[i].value['price'] * cartArray[i].count;
    }
    $("#allprice").html(`${sum} руб`)
}
renderCart()