import React from 'react';
import Map from '../components/MainMap'
import AppointmentBox from '../components/AppointmentBox';

function Home() {
  return (
      <div className='wrapper'>
        <div>
          <Map/>
        </div>
        <AppointmentBox/>
      </div>
  );
}

export default Home;
