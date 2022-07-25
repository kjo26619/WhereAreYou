import React from "react";
import cn from "classnames";

const Map = () => {
  return (
    <div className={cn("Map")}>
      <div className={cn("MapContainer")} id="map">
      </div>
    </div>
  );
};

export default Map;