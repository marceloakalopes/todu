class Todu < Formula
  desc "A simple to-do CLI app"
  homepage "https://github.com/marceloakalopes/todu"
  url "https://github.com/marceloakalopes/todu/releases/download/v0.1.0/todu-0.1.0.tar.gz"
  sha256 "7b7f94cdf4cee03195c1067e98d78b7d39790446350065bbda2ae8c809381032"
  version "0.1.0"

  def install
    bin.install "todu"  # Installs the binary in /usr/local/bin
  end

  def caveats
    <<~EOS
      Thanks for installing Todu CLI. You can manage tasks with:
      - todu list
      - todu new
    EOS
  end
end
