import React, { useEffect, useState } from "react";
import { Flex, Heading, Text, Button, Box } from "@chakra-ui/react";
import Logo from "../assets/Logo.svg";
import Image from "next/image";

const Navbar = () => {
  const [scrolling, setScrolling] = useState(false);

  const handleScroll = () => {
    if (window.scrollY > 50) {
      setScrolling(true);
    } else {
      setScrolling(false);
    }
  };

  useEffect(() => {
    window.addEventListener("scroll", handleScroll);
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  const navbarColor = scrolling ? "rgba(255, 255, 255, 0.8)" : "transparent";

  return (
    <Flex
      as="nav"
      align="center"
      justify="space-between"
      wrap="wrap"
      paddingX="50px"
      paddingY="30px"
      bg={navbarColor}
      color="white"
      position="fixed"
      top="0"
      left="0"
      right="0"
      zIndex="999"
      transition="background-color 0.3s"
    >
      <Image src={Logo} alt="Logo" />
      <Box display={{ base: "block", md: "none" }}>
        <Button
          display={{ base: "block", md: "none" }}
          onClick={() => {
            // Toggle mobile menu
          }}
        >
          Menu
        </Button>
      </Box>
      <Flex align="center">
        <Button color="black">Entrar</Button>
        <Button color="brand.900" backgroundColor="black" width="150px">
          Criar conta
        </Button>
      </Flex>
    </Flex>
  );
};

export default Navbar;
