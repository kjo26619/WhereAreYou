import React from "react";
import axios from 'axios';

function BlockTest() {

    const postClick = () => {
        const url = document.getElementById("address").value;
        const parameter = document.getElementById("parameter").value

        var params = new URLSearchParams();
        var params_enter_split = parameter.split('\n');
        
        for ( var i in params_enter_split) {
            
            var str_pm = params_enter_split[i].split(':');

            console.log(str_pm);
            params.append(str_pm[0], str_pm[1]);
            
        }
        console.log(params.toString())

        axios.post(url, params)
        .then((res) => {
            console.log(res);
        })
        .catch((error) => {
            console.log(error);
        })
    }

    const getClick = () => {
        var url = String(document.getElementById("address").value)
        var parameter = document.getElementById("parameter").value

        var params_enter_split = parameter.split('\n')
        
        url += "?"
        for ( var i in params_enter_split) {
            var str_pm = params_enter_split[i].split(':')

            url += str_pm[0] + "=" + str_pm[1]
            
            if (i -1 !== params_enter_split.length) {
                url += "&"
            }
        }

        console.log(url)
        axios.get(url)
        .then((res) => {
            console.log(res);
        })
        .catch((error) => {
            console.log(error);
        })
    }

    return (
        <div>
            <div>
                <h5>Address</h5>
                <input type="text" id="address"></input>

                <textarea id="parameter" rows="10" cols="30"></textarea>
                <button onClick={getClick}> GET </button>
                <button onClick={postClick}> POST </button>
            </div>
        </div>
    );
}

export default BlockTest;