import { Carousel } from "react-responsive-carousel";

function DescriptionPanel() {
  return (
    <div className="d-flex flex-column justify-content-center gradient-custom-2 h-100 mb-4">
      <div className="text-white px-3 py-4 p-md-5 mx-md-4">
        <Carousel
          className="carousel"
          infiniteLoop={true}
          autoPlay={true}
          showStatus={false}
          showArrows={false}
          showThumbs={false}
          showIndicators={false}
          interval={5000}
        >
          <div>
            <p class="small mb-0">
              1. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
              do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
              enim ad minim veniam, quis nostrud exercitation ullamco laboris
              nisi ut aliquip ex ea commodo consequat.
            </p>
          </div>
          <div>
            <p class="small mb-0">
              2. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
              do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
              enim ad minim veniam, quis nostrud exercitation ullamco laboris
              nisi ut aliquip ex ea commodo consequat.
            </p>
          </div>
          <div>
            <p class="small mb-0">
              3. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
              do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
              enim ad minim veniam, quis nostrud exercitation ullamco laboris
              nisi ut aliquip ex ea commodo consequat.
            </p>
          </div>
        </Carousel>
      </div>
    </div>
  );
}

export default DescriptionPanel;