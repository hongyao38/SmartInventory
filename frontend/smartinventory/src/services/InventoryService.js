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

// Send request to update a container's capacity
const updateBoxCapacity = async (container) => {
  const res = await axiosInstance({
    method: "put",
    url: "containers/" + sessionStorage.getItem("name"),
    data: container
  });
  return res.status === 200;
}

// Send request to add food to container
const addFoodToBox = async (i, j, food) => {
  const res = await axiosInstance({
    method: "put",
    url: "containers/" + sessionStorage.getItem("name") + "/" + i + "_" + j,
    data: food
  })
  return res.status === 200;
}

export {
  newBlock,
  getAllBlocks,
  newBox,
  getAllBoxes,
  updateBoxCapacity,
  addFoodToBox,
};
