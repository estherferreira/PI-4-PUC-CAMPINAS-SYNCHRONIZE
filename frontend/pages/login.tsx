import {
  Box,
  Center,
  Flex,
  Grid,
  GridItem,
  Input,
  Stack,
} from "@chakra-ui/react";
import Image from "next/image";
import LoginImage from "../assets/sandDuneGuy.png";

const Login = () => {
  return (
    <Box height="100vh">
      <Grid templateColumns="repeat(10, 1fr)">
        <GridItem colSpan={6} h="100vh" position="relative">
          <Image src={LoginImage} alt="Image" layout="fill" objectFit="cover" />
        </GridItem>
        <GridItem colSpan={4} h="100vh">
          <Flex alignItems="center" h="inherit" w="40vh">
            <Stack w="inherit">
              <Input
                placeholder="Digite seu e-mail"
                bgColor="offwhite"
                borderColor="white"
              />
              <Input
                placeholder="Digite sua senha"
                bgColor="offwhite"
                borderColor="white"
              />
            </Stack>
          </Flex>
        </GridItem>
      </Grid>
    </Box>
  );
};

export default Login;
