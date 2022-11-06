import axiosInstance from "../utils/AxiosInstance";

const newBlock = async (block) => {
    // Send requeset to backend
    const res = await axiosInstance({
        method: "post",
        url: "blocks/" + sessionStorage.getItem("name"),
        data: block,
    });
    console.log(res.status)
    return res.status === 201;
}

export {
    newBlock,
};