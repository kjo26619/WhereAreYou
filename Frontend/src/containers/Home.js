import React from 'react';
import Map from '../components/Map/Map'
import AppointmentBox from '../components/AppointmentBox';
import AppointmentList from '../components/AppointmentList';
import "./Home.scss";

function Home() {
  return (
      <div className='wrapper'>
        <div>
          <Map/>
        </div>
        <div>
        <AppointmentList/>
        </div>
        <AppointmentBox/>
      </div>
  );
}

export default Home;
