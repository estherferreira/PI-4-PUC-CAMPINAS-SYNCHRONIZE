import {
  Box,
  Flex,
  Grid,
  GridItem,
  Text,
  Input,
  Button,
  Divider,
} from "@chakra-ui/react";
import Image from "next/image";
import LoginImage from "../assets/sandDuneGuy.jpg";
import Logo from "../assets/Logo.svg";
import { IoIosArrowBack } from "react-icons/io";
import { useRouter } from "next/router";

const Login = () => {
  const router = useRouter();
  return (
    <Box height="100vh">
      <Grid templateColumns="repeat(10, 1fr)">
        <GridItem colSpan={6} h="100vh" position="relative">
          <Image src={LoginImage} alt="Image" layout="fill" objectFit="cover" />
        </GridItem>

        <GridItem colSpan={4} h="100vh" width="40vw">
          <Flex
            justifyContent="space-between"
            alignItems="center"
            margin="60px"
            marginTop="200px"
          >
            <Flex>
              <Image src={Logo} alt="Logo" />
            </Flex>
            <Flex
              cursor="pointer"
              alignItems="center"
              gap="10px"
              onClick={() => {
                router.push("/");
              }}
            >
              <IoIosArrowBack />
              <Text fontFamily="poppins.400" sx={{ textAlign: "center" }}>
                Voltar
              </Text>
            </Flex>
          </Flex>
          <Flex
            justifyContent="center"
            alignItems="center"
            w="inherit"
            direction="column"
            padding="48px"
            rowGap="48px"
            flexGrow={1}
          >
            <Box>
              <Text
                width="450px"
                fontFamily="inter.400"
                fontSize="sm"
                marginLeft="20px"
                marginBottom="10px"
              >
                E-mail
              </Text>
              <Input
                fontFamily="inter.400"
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
                fontFamily="inter.400"
                fontSize="sm"
                marginLeft="20px"
                marginBottom="10px"
              >
                Senha
              </Text>
              <Input
                fontFamily="inter.400"
                placeholder="Digite sua senha"
                bgColor="offwhite"
                borderColor="white"
                h="48px"
                w="35vw"
              />
            </Box>
            <Box>
              <Button
                fontFamily="inter.500"
                bgColor="brand.900"
                w="35vw"
                textColor="white"
              >
                Entrar
              </Button>
            </Box>
            <Divider color="gray" />
            <Text fontFamily="inter.400" fontSize="sm" display="flex">
              Ainda não tem uma conta?
              <Text
                fontFamily="inter.400"
                display="flex"
                color="brand.900"
                marginLeft="10px"
                cursor="pointer"
                onClick={() => router.push("/register")}
              >
                Criar conta
              </Text>
            </Text>
          </Flex>
        </GridItem>
      </Grid>
    </Box>
  );
};

export default Login;
