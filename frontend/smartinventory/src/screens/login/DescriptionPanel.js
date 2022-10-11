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
                    interval={2000}
                >
                    <div>
                        <p class="small mb-0 carousel-text-login">
                            Food wastage is a major source of greenhouse gases, accounting 
                            for around 8% of global greenhouse gas emissions In Singapore, 
                            food wastage makes up more than 10% of the total waste generated 
                            in Singapore.
                        </p>
                    </div>
                    <div>
                        <p class="small mb-0  carousel-text-login">
                            Food wastage exacerbates the worsening climate change 
                            crisis due to its significant carbon footprint.
                            As such, it is important, more now than ever, to reduce 
                            food waste. 
                        </p>
                    </div>
                    <div>
                        <p class="small mb-0  carousel-text-login">
                            Our solution is a centralised platform for consumers 
                            and supermarkets to carry out inventory 
                            tracking to allow better decision making. 
                        </p>
                    </div>
                </Carousel>
            </div>
        </div>
    );
}

export default DescriptionPanel;
