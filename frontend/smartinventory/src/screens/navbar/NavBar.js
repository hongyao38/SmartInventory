import "./NavBar.css";

import {
  MDBContainer, MDBNavbar, MDBNavbarItem, MDBNavbarNav
} from "mdb-react-ui-kit";
import React, { useState } from "react";

function NavBar() {
  const [sideNav, SetSideNav] = useState(true);

  const toggleNav = () => {
    SetSideNav(!sideNav);
    if (sideNav) {
      document.getElementById("mySideNav").style.width = "250px";
    } else {
      document.getElementById("mySideNav").style.width = "0px";
    }
  };

  return (
    <>
      <MDBNavbar expand="lg" light class="topnav">
        <MDBContainer fluid>
          <div class="flex-container">
            <div>
              <MDBNavbarNav className="me-auto mt-1 mb-lg-0">
                <MDBNavbarItem onClick={() => toggleNav()}>
                  <img src="/hamburgerIcon.png" alt="Hamburger Icon"/>
                </MDBNavbarItem>
              </MDBNavbarNav>
            </div>
            {/* <MDBContainer classname="ms-auto"> */}
            <div>
              <MDBNavbarNav className="mx-auto mt-1">
                <div class="flexrow1">
                  <MDBNavbarItem>
                    <h5>Welcome, {sessionStorage.getItem("name")}</h5>
                  </MDBNavbarItem>

                  <MDBNavbarItem>
                    <img src="/user.png" style={{ width: "35px" }} alt="User Profile" />
                  </MDBNavbarItem>
                </div>
              </MDBNavbarNav>
            </div>
          </div>
        </MDBContainer>
      </MDBNavbar>

      <div id="mySideNav" class="mainSideNav">
        {/* <a class="closebtn" onClick={() =>closeNav()}><img src ="close.png" style={{ width: "35px" }}/></a> */}
        <a href="/">Dashboard</a>
        <a href="/grid">Inventory</a>
        <a href="/alerts">Alerts</a>
        <a href="/">Logout</a>
      </div>
    </>
  );
}

export default NavBar;
