import React, { useEffect } from "react";
import cn from "classnames";
import "./Map.scss";

const kakao = window.kakao;

const Map = () => {
  let marker_list = [];
  let infowindow_list = [];

  useEffect(() => {
    
    let locPosition;
    let message;
    let nowUser = {
      "userId" : "test123",
      "x" : 10,
      "y" : 11,
    }

    function tempResponse(user, AppointmentNo){
      let ret = [];
      if(AppointmentNo >= 0){
        ret = [
          {
            "userId": "test1",
            "x" : 1,
            "y" : 1,
          },
          {
            "userId": "test2",
            "x" : 2,
            "y" : 2,
          },
          {
            "userId": "test3",
            "x" : 3,
            "y" : 3,
          }
        ]
      }
      return ret;
    }

    
    let container = document.getElementById("map");

    let options = {
      center: new kakao.maps.LatLng(37.26361647226481, 127.02860341503084),
      level: 4,
    };

    let map = new kakao.maps.Map(container, options);

    console.log("loading kakaomap");

    function getLocPosition () {
      if(navigator.geolocation){
        navigator.geolocation.getCurrentPosition(position => {
          let lat = position.coords.latitude, lon = position.coords.longitude;
          locPosition = new kakao.maps.LatLng(lat, lon);
          message = '현재 위치 표시';
        })
      }else {
        locPosition = kakao.maps.LatLng(37.26361647226481, 127.02860341503084);
        message = 'no geo';
      }

      return {locPosition, message};
    }
    // if (navigator.geolocation){
    //   navigator.geolocation.getCurrentPosition(function(position) {
    //     var lat = position.coords.latitude, lon = position.coords.longitude;
    //     var locPosition = new kakao.maps.LatLng(lat, lon), message = '현재 위치 표시'

    //     displayMarker(nowUser.userId, locPosition, message);
    //   })
    // }else{
    //   var locPosition = kakao.maps.LatLng(37.26361647226481, 127.02860341503084), message = 'no geo';

    //   displayMarker("empty", locPosition, message);
    // }
    

    function displayMarker(userId, locPosition, message){
      let marker = new kakao.maps.Marker({
        map: map,
        position: locPosition,
      });

      marker_list.push({
        "userId" :userId,
        "marker" :marker,
      });

      let iwContent = message, iwRemoveable = true;
      let infowindow = new kakao.maps.InfoWindow({
        content : iwContent,
        removable : iwRemoveable
      });
      
      infowindow_list.push({
        "userId": userId,
        "infowindow": infowindow
      });
      infowindow.open(map, marker);

      map.setCenter(locPosition);
    }

    let textbox = document.getElementById("textbox");
    
    kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
      var latlng = mouseEvent.latLng;
      
      var message = '클릭한 위치 : \n위도는 ' + latlng.getLat() + ' 이고,\n ';
      message += '경도는 ' + latlng.getLng() + ' 입니다';

      textbox.innerText = message;

      displayMarker(latlng, marker_list.length + "번째");
    });
    
    let locUpdate = setInterval(() => {
      let ret = getLocPosition();
      nowUser.x = ret.locPosition.La;
      nowUser.y = ret.locPosition.Ma;

      console.log(nowUser);

      tempResponse(nowUser, 0);
      //response 받은 배열 돌리면서 현재 마커랑 비교 => 좌표 다르면 지우고 다시보여주고 업데이트 (현재 로그인 유저 포함)
      //marker Map<userId, marker> 형태로 데이터 변경 필요 (CRUD 용이)
    },1000);
    
    return () => clearInterval(locUpdate);
    
  });

  return (
    <div className={cn("Map")}>
      <div className={cn("MapContainer")} id="map">
      </div>
    </div>
  );
};

export default Map;