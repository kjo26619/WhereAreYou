import React from 'react';
import Map from '../components/Map/Map'
import AppointmentBox from '../components/AppointmentBox';
import AppointmentList from '../components/AppointmentList';

function Home() {
  return (
      <div className='wrapper'>
        <div>
          <AppointmentList/>
          <Map/>
        </div>
        <AppointmentBox/>
      </div>
  );
}

export default Home;
