import React, { useEffect, useState } from "react";
import {
    MDBBtn,
    MDBContainer,
    MDBRow,
    MDBCol,
    MDBInput,
    MDBTypography,
    MDBValidation,
    MDBValidationItem,
    MDBCarousel,
    MDBCarouselItem,
} from "mdb-react-ui-kit";
import "mdb-react-ui-kit/dist/css/mdb.min.css";
import { useNavigate } from "react-router-dom";
import "react-responsive-carousel/lib/styles/carousel.min.css"; // requires a loader
import { Carousel } from "react-responsive-carousel";
import { Route, Link, Routes, useLocation } from "react-router-dom";

import "./LogInScreen.css";
import { testGet } from "../services/authService";


function ConfirmRegistration() {
    const getMethod = async () =>{
        try{
            const res = await testGet();
            console.log(res);
        }catch(err){
            console.log(err);
        }
    }

    return (
        <>
            <h1>login success</h1>
            <MDBBtn
                onClick={() => {
                    getMethod();
                }}
            >
                GET
            </MDBBtn>
        </>
    );
}

export default ConfirmRegistration;
