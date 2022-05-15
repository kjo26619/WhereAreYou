/* global kakao */
import React, { useEffect } from "react";
import cn from "classnames";
import "./Map.scss";

const kakao = window.kakao;

const Map = () => {
  let marker_list = [];
  let infowindow_list = [];

  function deleteAllMarker(){
    marker_list.forEach( (marker) => {
      marker.setMap(null);
    });

    infowindow_list.forEach( (infowindow) => {
      infowindow.close();
    });

    marker_list = [];
  }

  useEffect(() => {

    let container = document.getElementById("map");

    let options = {
      center: new kakao.maps.LatLng(37.26361647226481, 127.02860341503084),
      level: 4,
    };

    let map = new kakao.maps.Map(container, options);

    console.log("loading kakaomap");

    if (navigator.geolocation){
      navigator.geolocation.getCurrentPosition(function(position) {
        var lat = position.coords.latitude, lon = position.coords.longitude;
        var locPosition = new kakao.maps.LatLng(lat, lon), message = '현재 위치 표시'

        displayMarker(locPosition, message);
      })
    }else{
      var locPosition = kakao.maps.LatLng(37.26361647226481, 127.02860341503084), message = 'no geo';

      displayMarker(locPosition, message);
    }
    

    function displayMarker(locPosition, message){
      let marker = new kakao.maps.Marker({
        map: map,
        position: locPosition
      });

      marker_list.push(marker);

      let iwContent = message, iwRemoveable = true;
      let infowindow = new kakao.maps.InfoWindow({
        content : iwContent,
        removable : iwRemoveable
      });
      
      infowindow_list.push(infowindow);
      infowindow.open(map, marker);

      // map.setCenter(locPosition);
    }

    let textbox = document.getElementById("textbox");
    
    kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
      var latlng = mouseEvent.latLng;
      
      var message = '클릭한 위치 : \n위도는 ' + latlng.getLat() + ' 이고,\n ';
      message += '경도는 ' + latlng.getLng() + ' 입니다';

      textbox.innerText = message;

      displayMarker(latlng, marker_list.length + "번째");
    });
    
  }, []);

  return (
    <div className={cn("Map")}>
      <div className={cn("MapContainer")} id="map">
      </div>
<<<<<<< HEAD
      <div>
        <button className={cn("Button")} id="button2" onClick={deleteAllMarker}>Delete All Marker</button>
      </div>
      <div>
        <label className={cn("TextBox")} id="textbox"> here? </label>
      </div>
=======
>>>>>>> 57b04b5... Modal
    </div>
  );
};

export default Map;