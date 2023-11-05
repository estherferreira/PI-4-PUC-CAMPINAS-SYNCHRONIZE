import {
  Box,
  Center,
  Flex,
  Grid,
  GridItem,
  Text,
  Input,
  Stack,
  Button,
  Divider,
} from "@chakra-ui/react";
import Image from "next/image";
import RegisterImage from "../assets/manRunning.jpg";
import Logo from "../assets/Logo.svg";

const Register = () => {
  return (
    <Box height="100vh">
      <Grid templateColumns="repeat(10, 1fr)">
        <GridItem colSpan={6} h="100vh" position="relative">
          <Image
            src={RegisterImage}
            alt="Image"
            layout="fill"
            objectFit="cover"
          />
        </GridItem>
        <GridItem colSpan={4} h="100vh" width="40vw">
          <Flex
            justifyContent="center"
            alignItems="center"
            h="inherit"
            w="inherit"
            direction="column"
            padding="48px"
            rowGap="48px"
            flexGrow={1}
          >
            <Flex>
              <Image src={Logo} alt="Logo" />
            </Flex>
            <Box>
              <Text
                width="450px"
                fontWeight="inter.400"
                fontSize="sm"
                marginLeft="20px"
                marginBottom="10px"
              >
                E-mail
              </Text>
              <Input
                placeholder="Digite seu e-mail"
                bgColor="offwhite"
                borderColor="white"
                h="48px"
                w="35vw"
              />
            </Box>
            <Box>
              <Text
                width="450px"
                fontWeight="inter.400"
                fontSize="sm"
                marginLeft="20px"
                marginBottom="10px"
              >
                Senha
              </Text>
              <Input
                placeholder="Digite sua senha"
                bgColor="offwhite"
                borderColor="white"
                h="48px"
                w="35vw"
              />
            </Box>
            <Box>
              <Button bgColor="brand.900" w="35vw" textColor="white">
                Criar conta
              </Button>
            </Box>
            <Divider />
            <Box display="flex" alignItems="center">
              <Text fontWeight="inter.400" fontSize="sm">
                Ja tenho uma conta
              </Text>
              <Text
                fontWeight="inter.400"
                color="brand.900"
                marginLeft="10px"
                cursor="pointer"
              >
                Entrar
              </Text>
            </Box>
          </Flex>
        </GridItem>
      </Grid>
    </Box>
  );
};

export default Register;
