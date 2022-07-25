import React, { useEffect } from "react";
import Map from './Map';
import "./MainMap.scss";

const kakao = window.kakao;

const MainMap = () => {
  let marker_list = [];
  let infowindow_list = [];

  useEffect(() => {
    function deleteAllMarker(){
      marker_list.forEach( (content) => {
        content.marker.setMap(null);
      });
  
      infowindow_list.forEach( (content) => {
        content.infowindow.close();
      });
  
      marker_list = [];
    }

    let locPosition;
    let message;
    let nowUser = {
      "userId" : "test123",
      "x" : 127.0507571, //lat
      "y" : 37.2440589, //lng
    }

    let tmpCnt = 0;
    function tempResponse(user, AppointmentNo){
      let ret = [];
      if(AppointmentNo >= 0){
        ret = [
          {
            "userId": "test1",
            "x" : 37.26361647226481,
            "y" : 127.02860341503084,
          },
          {
            "userId": "test2",
            "x" : 37.26361647226482,
            "y" : 127.02870341503085,
          },
          // {
          //   "userId": "test3",
          //   "x" : 37.26362647226491,
          //   "y" : 127.02860341503094,
          // }
        ]
      }
      ret[0].x += (tmpCnt) * 0.0001;
      ret[1].y += (tmpCnt) * 0.0001;
      tmpCnt +=1;
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
    
    
    let locUpdate = setInterval(() => {
      let ret = getLocPosition();
      let bounds = new kakao.maps.LatLngBounds();

      try{
        nowUser.x = ret.locPosition.La;
        nowUser.y = ret.locPosition.Ma;
      }catch{
        console.log("try test");
      }
      deleteAllMarker();

      let retArr = tempResponse(nowUser, 0);

      retArr.forEach( content => {
        let locPosition = new kakao.maps.LatLng(content.x, content.y);
        bounds.extend(locPosition);
        
        // console.log(locPosition);
        displayMarker(content.userId, locPosition, message);

      });
      
      map.setBounds(bounds);
      //response 받은 배열 돌리면서 현재 마커랑 비교 => 좌표 다르면 지우고 다시보여주고 업데이트 (현재 로그인 유저 포함)
      //marker Map<userId, marker> 형태로 데이터 변경 필요 (CRUD 용이)
    },1000);
    
    return () => clearInterval(locUpdate);
    
  });

  return (
    <Map/>
  );
};

export default MainMap;