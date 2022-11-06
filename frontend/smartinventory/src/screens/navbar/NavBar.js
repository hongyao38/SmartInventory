import * as mdb from 'mdb-react-ui-kit'; // lib
import "./NavBar.css";

import React, { useState } from 'react';
import {
  MDBNavbar,
  MDBContainer,
  MDBNavbarNav,
  MDBNavbarItem,

} from 'mdb-react-ui-kit';


function Test() {
const [sideNav, SetSideNav] = useState(true)

const toggleNav = () => {
  // console.log("hello")
  SetSideNav(!sideNav);
  if(sideNav){
  document.getElementById('mySidenav').style.width = "250px"
}else{
  document.getElementById('mySidenav').style.width = "0px"
}
}

  return (
    <>
    
    <MDBNavbar expand='lg' light class="topnav" >
      <MDBContainer fluid>
      <div class='flex-container'>
        <div>
          <MDBNavbarNav className='me-auto mt-1 mb-lg-0'>
            <MDBNavbarItem onClick={() => toggleNav()}>
                <img src="hamburgerIcon.png"/>
            </MDBNavbarItem>
          </MDBNavbarNav>
        </div>
          {/* <MDBContainer classname="ms-auto"> */}
          <div>
          <MDBNavbarNav className='mx-auto mt-1'>
  
              <div class='flexrow1'>
            <MDBNavbarItem >
                <h5 >Welcome, user</h5>
            </MDBNavbarItem>
            
            <MDBNavbarItem>
                <img src="user.png" style={{ width: "35px" }}/>
            </MDBNavbarItem>
            </div>

          </MDBNavbarNav>
          </div>
        </div>
      </MDBContainer>
    </MDBNavbar>

    <div id="mySidenav" class="sidenav">
  {/* <a class="closebtn" onClick={() =>closeNav()}><img src ="close.png" style={{ width: "35px" }}/></a> */}
  <a href="#">Account</a>
  <a href="#">dashboard</a>
  <a href="#">Clients</a>
  <a href="#">Contact</a>
</div>
</>
);
}

export default Test;
