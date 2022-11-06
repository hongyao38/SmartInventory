import axiosInstance from "../utils/AxiosInstance";

const newBlock = async (block) => {
    // Send requeset to backend
    const res = await axiosInstance({
        method: "post",
        url: "blocks/" + sessionStorage.getItem("name"),
        data: block
    });
    return res.status === 201;
}

const getAllBlocks = async () => {
    const res = await axiosInstance({
        method: "get",
        url: "blocks/" + sessionStorage.getItem("name")
    })
    return res.data;
}

export {
    newBlock,
    getAllBlocks,
};