import axiosInstance from "../utils/AxiosInstance";

// Send request to make a new block
const newBlock = async (block) => {
  const res = await axiosInstance({
    method: "post",
    url: "blocks/" + sessionStorage.getItem("name"),
    data: block,
  });
  return res.status === 201;
};

// Send request to get all blocks belonging to user
const getAllBlocks = async () => {
  const res = await axiosInstance({
    method: "get",
    url: "blocks/" + sessionStorage.getItem("name"),
  });
  return res.data;
};

// Send request to make a new container
const newBox = async (container) => {
  const res = await axiosInstance({
    method: "post",
    url: "containers/" + sessionStorage.getItem("name"),
    data: container,
  });
  return res.status === 201;
};

// Send request to get all containers belonging to user
const getAllBoxes = async () => {
  const res = await axiosInstance({
    method: "get",
    url: "containers/" + sessionStorage.getItem("name"),
  });
  return res.data;
};

export {
  newBlock,
  getAllBlocks,
  newBox,
  getAllBoxes,
};
